package creative.air.mail.pojo;

import javax.mail.Flags;

public class MailMessage {
	String personal, address, subject;
	boolean flag;
	int size;
	Object content;

	public MailMessage(String personal, String address, Flags flags, String subject, int size, Object content) {
		this.personal = personal;
		this.address = address;
		this.flag = flags.contains(Flags.Flag.SEEN);
		this.subject = subject;
		this.size = size;
		this.content = content;
	}

	public String getPersonal() {
		return personal;
	}

	public String getAddress() {
		return address;
	}

	public String getSubject() {
		return subject;
	}

	public boolean getFlag() {
		return flag;
	}

	public int getSize() {
		return size;
	}

	public Object getContent() {
		return content;
	}

	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append("personal=").append(this.personal);
		buff.append(" address=").append(this.address);
		buff.append(" flag=").append(this.flag);
		buff.append(" subject=").append(this.subject);
		buff.append(" size=").append(this.size);
		buff.append(" content=").append(this.content);
		return buff.toString();
	}
}
