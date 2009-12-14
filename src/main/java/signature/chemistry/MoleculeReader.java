package signature.chemistry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoleculeReader {
    
    public static List<Molecule> readSDFFile(String filename) {
        List<Molecule> molecules = new ArrayList<Molecule>();
        try {
            File file = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            List<String> block = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("$$$$")) {
                    Molecule molecule;
                    try {
                        molecule = MoleculeReader.makeMolecule(block); 
                        molecules.add(molecule);
                    } catch (Exception e) {
                        System.err.println("failed for block " + i + " " + e);
                        e.printStackTrace();
                    }
                    block.clear();
                    i++;
                } else {
                    block.add(line);
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe.toString());
        } 
        return molecules;
    }
    
    private static Molecule makeMolecule(List<String> block) throws Exception {
        Molecule molecule = new Molecule();
        // counts are on the fourth line
        String countLine = block.get(3);
        int atomCount = Integer.parseInt(countLine.substring(0, 3).trim());
        int bondCount = Integer.parseInt(countLine.substring(3, 6).trim());
        
        // atom block starts on the fifth line (4th index)
        int atomLineStart = 4;
        int atomLineEnd   = atomCount + atomLineStart; 
        for (int i = atomLineStart; i < atomLineEnd; i++) {
            String symbol = block.get(i).substring(30, 33).trim();
            int atomIndex = i - atomLineStart;
            molecule.addAtom(atomIndex, symbol);
        }
        
        if ( atomCount > 1) {
        	// bond block starts right after the atom block
        	int bondLineStart = atomLineEnd;
        	int bondLineEnd   = bondLineStart + bondCount;
        	for (int i = bondLineStart; i < bondLineEnd; i++) {
        		String bondLine = block.get(i);
        		int atomNumberA = Integer.parseInt(bondLine.substring(0, 3).trim());
        		int atomNumberB = Integer.parseInt(bondLine.substring(3, 6).trim());
        		int order = Integer.parseInt(bondLine.substring(7, 10).trim());
        		molecule.addBond(atomNumberA - 1, atomNumberB - 1, order);
        	}
        }
        return molecule;
    }

}
