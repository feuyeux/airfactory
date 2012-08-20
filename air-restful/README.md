jerseyTest
==========
<properties>
	<jersey.version>1.12</jersey.version>
	<spring.version>3.0.3.RELEASE</spring.version>
	<!-- 2.5.6.SEC03 -->
	<slf4j.version>1.4.3</slf4j.version>
	<log4j.version>1.2.16</log4j.version>
	<mysql.version>5.0.5</mysql.version>
	<hibernate.version>3.5.1-Final</hibernate.version>
	<junit.version>4.10</junit.version>
	<netbeans.hint.deploy.server>gfv3ee6</netbeans.hint.deploy.server>
</properties>

<dependencies>
	<!-- jersey -->
	<dependency>
		<groupId>com.sun.jersey.contribs</groupId>
		<artifactId>jersey-spring</artifactId>
		<version>${jersey.version}</version>
	</dependency>

	<dependency>
		<groupId>com.sun.jersey</groupId>
		<artifactId>jersey-json</artifactId>
		<version>${jersey.version}</version>
	</dependency>
	<!-- spring -->
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-orm</artifactId>
		<version>${spring.version}</version>
		<exclusions>
			<exclusion>
				<groupId>org.springframework</groupId>
				<artifactId>spring-core</artifactId>
			</exclusion>
			<exclusion>
				<groupId>org.springframework</groupId>
				<artifactId>spring-beans</artifactId>
			</exclusion>
			<exclusion>
				<groupId>org.springframework</groupId>
				<artifactId>spring-context</artifactId>
			</exclusion>
			<exclusion>
				<groupId>org.springframework</groupId>
				<artifactId>spring-aop</artifactId>
			</exclusion>
			<exclusion>
				<groupId>org.springframework</groupId>
				<artifactId>spring-jdbc</artifactId>
			</exclusion>

		</exclusions>
	</dependency>

	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-test</artifactId>
		<version>${spring.version}</version>
	</dependency>

	<!-- unit test -->
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit</artifactId>
		<version>${junit.version}</version>
		<scope>test</scope>
	</dependency>

	<!-- log -->
	<dependency>
		<groupId>org.slf4j</groupId>
		<artifactId>slf4j-log4j12</artifactId>
		<version>${slf4j.version}</version>
	</dependency>

	<!-- JPA2 -->
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>${mysql.version}</version>
	</dependency>

	<dependency>
		<groupId>org.hibernate</groupId>
		<artifactId>hibernate-annotations</artifactId>
		<version>${hibernate.version}</version>
		<exclusions>
			<exclusion>
				<groupId>org.slf4j</groupId>
				<artifactId>slf4j-api</artifactId>
			</exclusion>
		</exclusions>
	</dependency>

	<dependency>
		<groupId>org.hibernate</groupId>
		<artifactId>hibernate-entitymanager</artifactId>
		<version>${hibernate.version}</version>
	</dependency>
</dependencies>