import java.util.Comparator;

public class InstanceComparator implements Comparator<Instance>{

	@Override
	public int compare(Instance o1, Instance o2) {
		return o1.diff - o2.diff;
	}

}
