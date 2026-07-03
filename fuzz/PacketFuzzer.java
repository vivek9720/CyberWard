import packet.*; public final class PacketFuzzer{public static void fuzzerTestOneInput(byte[] data){new PacketInspector().inspectPcap(data);if(data!=null)new PacketInspector().inspectFrame(data);}}
