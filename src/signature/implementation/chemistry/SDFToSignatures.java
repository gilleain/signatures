package signature.implementation.chemistry;

public class SDFToSignatures {

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage : SDFToSignatures <filename>");
        }
        String filename = args[0];
        for (Molecule molecule : MoleculeReader.readSDFFile(filename)) {
            MoleculeSignature signature = new MoleculeSignature(molecule);
            // get graph signature
            System.out.println(signature.toString());
        }
    }

}
