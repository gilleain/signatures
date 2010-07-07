package signature.chemistry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MoleculeWriter {
    
    public static void writeMolfile(String filename, Molecule molecule) {
        File file = new File(filename);
        try {
            writeToStream(new FileOutputStream(file), molecule);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeToStream(OutputStream stream, Molecule molecule) {
        try {
            BufferedWriter writer = 
                new BufferedWriter(new OutputStreamWriter(stream));
            writeHeader(writer, molecule);
            for (int i = 0; i < molecule.getAtomCount(); i++) {
                writeAtom(writer, molecule, i);
            }
            for (int i = 0; i < molecule.getBondCount(); i++) {
                writeBond(writer, molecule, i);
            }
            writer.write("M  END");
            writer.newLine();
            writer.close();
        } catch (IOException ioe) {

        }
    }

    private static void writeHeader(
            BufferedWriter writer, Molecule molecule) throws IOException {
        writer.newLine();
        writer.write(" Writen by signature package");
        writer.newLine();
        writer.newLine();
        int a = molecule.getAtomCount();
        int b = molecule.getBondCount();
        writer.write(
                String.format("%3d%3d  0  0  0  0  0  0  0  0999 V2000", a, b));
        writer.newLine();
    }
    
    private static void writeAtom(
            BufferedWriter writer, Molecule molecule, int i) throws IOException {
        String empty3DCoords = "    0.0000    0.0000    0.0000 ";
        String emptyTail = " 0  0  0  0  0  0  0  0  0  0  0  0";
        String symbol = molecule.getSymbolFor(i);
        writer.write(empty3DCoords + String.format("%-3s", symbol) + emptyTail);
        writer.newLine();
    }

    private static void writeBond(
            BufferedWriter writer, Molecule molecule, int i) throws IOException {
        int f = molecule.getFirstInBond(i) + 1;
        int s = molecule.getSecondInBond(i) + 1;
        int o = molecule.getBondOrderAsInt(i);
        writer.write(String.format("%3d%3d%3d  0  0  0  0", f, s, o));
        writer.newLine();
    }
}
