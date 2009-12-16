package signature.simple;

import signature.AbstractVertexSignature;

public class SimpleVertexSignature extends AbstractVertexSignature {
    
    public SimpleVertexSignature() {
        super("[", "]");
    }

    @Override
    public int[] getConnected(int vertexIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getEdgeSymbol(int vertexIndex, int otherVertexIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getVertexCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getVertexSymbol(int vertexIndex) {
        // TODO Auto-generated method stub
        return null;
    }

}
