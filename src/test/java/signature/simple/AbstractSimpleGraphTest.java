package signature.simple;

public class AbstractSimpleGraphTest {
    
    public static SimpleGraph makeCube() {
        SimpleGraph cube = 
            new SimpleGraph("0:1,0:3,0:7,1:2,1:6,2:3,2:5,3:4,4:5,4:7,5:6,6:7");
        return cube;
    }
    
    public static SimpleGraph makeAdamantane() {
        SimpleGraph adamantane =
            new SimpleGraph("0:1,0:2,0:3,1:4,2:5,3:6,4:7,4:9,5:7,5:8,6:8,6:9");
        return adamantane;
    }
    
    public static SimpleGraph makeCuneane() {
        SimpleGraph cuneane = 
            new SimpleGraph("0:1,0:3,0:5,1:2,1:7,2:3,2:7,3:4,4:5,4:6,5:6,6:7");
        return cuneane;
    }
    
    public static SimpleGraph makeTwistane() {
        SimpleGraph twistane =
            new SimpleGraph("0:1,0:2,0:3,1:4,1:5,2:6,3:7,4:8,5:9,6:8,6:9,7:9");
        return twistane;
    }

    public static SimpleGraph makeNapthalene() {
        SimpleGraph napthalene = 
            new SimpleGraph("0:1,0:5,1:2,1:6,2:3,2:9,3:4,4:5,6:7,7:8,8:9");
        return napthalene;
    }

}
