.PHONY: source
source: akm/xx1.class
	@echo make OK

akm/xx1.class: xx1.java akm/RSACrypto.class
	javac -d . xx1.java

akm/RSACrypto.class: RSACrypto.java
	javac -d . RSACrypto.java

.PHONY: clean
clean:
	
