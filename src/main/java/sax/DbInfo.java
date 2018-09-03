package sax;

public enum DbInfo {
	
	Local("net.sourceforge.jtds.jdbc.Driver","sa","ezoa","jdbc:jtds:sqlserver://localhost:1433/AuditSystemDB;instance=SQLSERVER2008R2;SelectMethod=Cursor"),
	TEST("net.sourceforge.jtds.jdbc.Driver","sa","ezoa","jdbc:jtds:sqlserver://192.168.0.24:1433/AuditSystemDB;SelectMethod=Cursor"),
	M122("net.sourceforge.jtds.jdbc.Driver","sa","yida@2014","jdbc:jtds:sqlserver://10.96.245.122:1433/AuditSystemDB;SelectMethod=Cursor"),
	M115("net.sourceforge.jtds.jdbc.Driver","sa","yida@2014","jdbc:jtds:sqlserver://10.96.245.115:1433/AuditSystemDB;SelectMethod=Cursor"),
	M20("net.sourceforge.jtds.jdbc.Driver","sa","yida@2014","jdbc:jtds:sqlserver://192.168.102.20:1433/AuditSystemDB;SelectMethod=Cursor"),
	M21("net.sourceforge.jtds.jdbc.Driver","sa","yida@2014","jdbc:jtds:sqlserver://192.168.102.21:1433/AuditSystemDB;SelectMethod=Cursor");

	String driver;
	String user;
	String password;
	String url;
	
	private DbInfo(String driver, String user, String password, String url) {
		this.driver = driver;
		this.user = user;
		this.password = password;
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
