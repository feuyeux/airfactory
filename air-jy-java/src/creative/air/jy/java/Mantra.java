package creative.air.jy.java;

public class Mantra {

	public String fog(String word) {
		try {
			byte[] bs = word.getBytes();
			byte[] bs1 = new byte[bs.length];
			int n = 0;
			for (byte b : bs) {
				bs1[n++] = b += 1;
			}
			String mantra = new String(bs1);
			return mantra;
		} catch (Exception e) {
			return word;
		}
	}

	public String unfog(String word) {
		try {
			byte[] bs = word.getBytes();
			byte[] bs1 = new byte[bs.length];
			int n = 0;
			for (byte b : bs) {
				bs1[n++] = b -= 1;
			}
			String mantra = new String(bs1);
			return mantra;
		} catch (Exception e) {
			return word;
		}
	}
}
