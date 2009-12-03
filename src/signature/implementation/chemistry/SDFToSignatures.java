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
        int molCount = 0;
        for (Molecule molecule : MoleculeReader.readSDFFile(filename)) {
        	try {
        		MoleculeSignature signature = new MoleculeSignature(molecule);
        		// get graph signature
        		System.out.println(signature.getGraphSignature());
        		molCount++;
        		System.out.println("Current molecule: " + molCount);
        	}
        	catch (Exception e) {}
        }
        System.out.println("Total number of molecules: " + molCount);
    }

}
