package signature.simple;

public class AbstractSimpleGraphTest {
    
    public static SimpleGraph makePetersensGraph() {
        return new SimpleGraph(
                "0:1,0:5,0:4,1:2,1:6,2:3,2:7,3:8,3:4,4:9,5:7,5:8,6:8,6:9,7:9");
    }
    
    public static SimpleGraph makeTietzesGraph() {
        return new SimpleGraph(
                "0:1,0:4,0:8,1:2,1:6,2:3,2:10,3:4,3:7,4:5," +
                "5:6,5:11,6:7,7:8,8:9,9:10,9:11,10:11");
            
    }
    
    public static SimpleGraph makeTruncatedTetrahedron() {
        return 
            new SimpleGraph("0:1,0:2,0:3,1:2,1:4,2:5,3:6,3:7,4:8,4:9,5:10,5:11," 
                    + "6:7,6:8,7:11,8:9,9:10,10:11");
    }

    public static SimpleGraph makeDoubleBridgedPentagon() {
        return new SimpleGraph("0:1,0:2,1:3,1:5,1:6,2:4,2:5,2:6,3:4,3:5,4:6");
    }
    
    public static SimpleGraph makeCube() {
        return new SimpleGraph(
                "0:1,0:3,0:7,1:2,1:6,2:3,2:5,3:4,4:5,4:7,5:6,6:7");
    }
    
    public static SimpleGraph makeAdamantane() {
        return new SimpleGraph(
                "0:1,0:2,0:3,1:4,2:5,3:6,4:7,4:9,5:7,5:8,6:8,6:9");
    }
    
    public static SimpleGraph makeCuneane() {
        return new SimpleGraph(
                "0:1,0:3,0:5,1:2,1:7,2:3,2:7,3:4,4:5,4:6,5:6,6:7");
    }
    
    public static SimpleGraph makeTwistane() {
        return new SimpleGraph(
                "0:1,0:2,0:3,1:4,1:5,2:6,3:7,4:8,5:9,6:8,6:9,7:9");
    }

    public static SimpleGraph makeNapthalene() {
        return  new SimpleGraph("0:1,0:5,1:2,1:6,2:3,2:9,3:4,4:5,6:7,7:8,8:9");
    }
    
    public static SimpleGraph makeSquareQuotientGraph() {
        // a very odd graph designed to have a square quotient graph
        return 
            new SimpleGraph(
                 "0:1,0:2,0:9,1:3,1:4,1:5,2:6,2:7,2:8,3:9,4:9,5:9,6:9,7:9,8:9");
    }
    
    public static SimpleGraph makeBowtieane() {
        // don't know the name of the structure
        return new SimpleGraph(
                "0:1,0:7,1:2,1:8,2:3,2:6,3:4,3:9,4:5,5:6,5:9,6:7,7:8,8:9");
    }
    
    public static SimpleGraph makeDiSpiroOctane() {
        return new SimpleGraph("0:1,0:2,1:2,1:3,1:4,2:3,2:6,3:5,3:7,4:5,6:7");
    }

}
