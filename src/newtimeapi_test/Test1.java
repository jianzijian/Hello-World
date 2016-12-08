package newtimeapi_test;

import java.time.Duration;
import java.time.Instant;

class Test1 {

	public static void main(String[] args) {
		Instant start1 = Instant.now();
		for(int i=0;i<100000;i++){}
		Instant end1 = Instant.now();
		Duration timeElapsed1=Duration.between(start1, end1);
		System.out.println(timeElapsed1.toMillis());
	}
}
