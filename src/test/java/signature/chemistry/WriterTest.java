package signature.chemistry;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import signature.simple.SimpleGraph;
import signature.simple.SimpleGraphFactory;

public class WriterTest {
    
    @Test
    public void minimalTest() {
       Molecule mol = new Molecule();
       mol.addAtom("C");
       mol.addAtom("O");
       mol.addAtom("N");
       mol.addSingleBond(0, 1);
       mol.addBond(0, 2, 2);
       MoleculeWriter.writeToStream(System.out, mol);
    }
    
    public Molecule moleculeFromSimpleGraph(SimpleGraph simpleGraph) {
        Molecule mol = new Molecule();
        mol.addMultipleAtoms(simpleGraph.getVertexCount(), "C");
        for (SimpleGraph.Edge edge : simpleGraph.edges) {
            mol.addSingleBond(edge.a, edge.b);
        }
        return mol;
    }
    
    @Test
    public void writeSimpleGraphExamples() {
        File tmpDir = new File("tmp");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        for (Method method : SimpleGraphFactory.class.getMethods()) {
            Class returnClass = method.getReturnType();
            
            // ignore the methods that take arguments
            if (method.getParameterTypes().length > 0) continue;
            
            if (returnClass == SimpleGraph.class) {
                try {
                    SimpleGraph graph = (SimpleGraph) method.invoke(null);
                    Molecule molecule = moleculeFromSimpleGraph(graph);
                    String name = method.getName().substring(4);
                    String filename = String.format("tmp/%s.mol", name);
                    MoleculeWriter.writeMolfile(filename, molecule);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
