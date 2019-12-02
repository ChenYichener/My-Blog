public class Kafka {

    public static void main(final String[] args) {

        final ReplicaManager replicaManager = new ReplicaManager();
        replicaManager.loadReplicasFromDisk();
    }
}
