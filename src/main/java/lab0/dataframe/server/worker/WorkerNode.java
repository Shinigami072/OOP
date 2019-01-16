package lab0.dataframe.server.worker;

import lab0.dataframe.DataFrame;
import lab0.dataframe.DataFrameThreaded;
import lab0.dataframe.DefultSingletons;
import lab0.dataframe.exceptions.DFApplyableException;
import lab0.dataframe.server.protocol.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

public class WorkerNode {
    public static WorkerProtocolParser parser;
    public static Random rand = new Random();

    public static void main(String... args) throws IOException {

        //OPtions setup
        if (args.length != 1)
            throw new IllegalArgumentException("requires server address");
        System.out.println(args[0]);
        int port = args[0].indexOf(':');
        if (port > 0) {
            PortType.WORKER.setPort(Integer.parseInt(args[0].substring(port)));
        }

        port = port < 0 ? args[0].length() : port;
        int workerCount = DefultSingletons.coreCount;


        //Connect to server;
        Socket sock = new Socket(args[0].substring(0, port), PortType.WORKER.getPort());
        ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

        System.out.println("Announcing to " + sock.getInetAddress());
        parser = new WorkerProtocolParser(in, out);
        parser.announcePacket(workerCount);

        System.out.println("Task Loop");
        try{
        while (true) {
            WorkerCommType type = parser.readType();
            System.out.println(type);
            switch (type) {

                default:
                case TASK_COMPLETED:
                case TASK_REJECTED:
                case ANNOUNCE:
                    throw new IllegalStateException("Unexpected State");
                case DISCONNECT:
                    //todo - signal disconnectPacket

                    return;


                case TASK_SCHEDULED:
                    try {
                        executeTask(parser.readTask());
                    } catch (InterruptedException | DFApplyableException e) {
                        e.printStackTrace();
                        parser.rejectPacket();
                    }
                    break;

                case HEARTBEAT:
                    //todo: disconnection detection
                    break;
            }
        }
        }finally {
            parser.disconnectPacket();
            sock.close();
        }


    }

    private static void executeTask(Task task) throws IOException, InterruptedException, DFApplyableException {
        switch (task) {
            case NONE:
                break;
            case DUMMY:
                Thread.sleep(rand.nextInt(2000));
                parser.completePacket();
                break;
            case GROUP:
                System.out.println(task + " started");

                if (parser.readInt() != 2)
                    throw new IllegalStateException("Wrong apply packet");

                String[] names = parser.readColnames();

                DataFrameThreaded threaded = new DataFrameThreaded(DefultSingletons.defaultExecutor, parser.writeDataFrame());
                DataFrameThreaded.GroupHolderThreaded groups = (DataFrameThreaded.GroupHolderThreaded) threaded.groupBy(names);
                Map<DataFrame.ValueGroup,DataFrame>grp =groups.getGroups();
                System.out.println(grp);
                System.out.println(task + "  ended");
                parser.completePacket(grp);

                break;
            case APPLY:
                System.out.println(task + " started");

                if (parser.readInt() != 2)
                    throw new IllegalStateException("Wrong apply packet");

                ApplyOperation op = parser.readApplyOperation();

                DataFrame df = parser.writeDataFrame();
                System.out.println(df);
                DataFrame apply = op.getApplyable().apply(df);

                parser.completePacket(apply);
                System.out.println(task + "  ended");
                break;

        }
    }
}

