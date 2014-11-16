package example.GEPsample;
import edu.ccil.ec.Genotype;
import edu.ccil.ec.genotype.arraylist.UtilGenome;
import edu.ccil.ec.genotype.gep.INodeFactory;
import edu.ccil.ec.genotype.gep.Node;
import edu.ccil.ec.genotype.gep.NodeArrayListGenome;
import edu.ccil.ec.genotype.gep.UtilGEP;
import edu.ccil.ec.genotype.gep.math.*;

public class myGenome extends NodeArrayListGenome {

	private static final long serialVersionUID = 1L;

	protected static Variable<Boolean> A = new Variable<Boolean>("A");
	protected static Variable<Double> a = new Variable<Double>("a");
	static {
		NodeArrayListGenome.function = new INodeFactory<?>[] { new Add(), new Subtract(), new Multiply()};
		//NodeArrayListGenome.terminal = new INodeFactory<?>[] {A,a,new Constant.BooleanFactory(),new Constant.DoubleFactory(5, 10),};
		NodeArrayListGenome.terminal = new INodeFactory<?>[] {a,new Constant.DoubleFactory(9, 10),};

	}

	public myGenome() {
		super(5);
		initialSize=5;
		return;
	}


	public void recombineArrayListOnePoint(myGenome genome, double recombinationRate) {
		UtilGenome.recombineArrayListOnePoint(this, genome, recombinationRate);
		return;
	}
	
	public void recombineArrayListTwoPoint(myGenome genome, double recombinationRate) {
		UtilGenome.recombineArrayListTwoPoint(this, genome, recombinationRate);
		return;
	}
	
	public void recombineArrayListUniform(myGenome genome, double recombinationRate) {
		UtilGenome.recombineArrayListUniform(this, genome, recombinationRate);
		return;
	}
	
	public void mutateArrayListSwitch(double mutationRate) {
		UtilGEP.mutateArrayListSwitch(this, mutationRate);
		return;
	}
	
	public void mutateArrayListShift(double mutationRate) {
		UtilGEP.mutateArrayListShift(this, mutationRate);
		return;
	}
	
	public void mutateArrayListReverse(double mutationRate) {
		UtilGEP.mutateArrayListReverse(this, mutationRate);
		return;
	}


	@Override
	public myGenome create() { 
		return new myGenome();
	}

	@Override
	public myGenome clone() { 
	return (myGenome)super.clone();
	}

}