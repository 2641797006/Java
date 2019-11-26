source: akm/xx1.class
	@echo make OK

akm/xx1.class: xx1.java akm/DESCrypto.class
	javac -d . xx1.java

akm/DESCrypto.class: DESCrypto.java akm/Digest.class
	javac -d . DESCrypto.java

akm/Digest.class: Digest.java
	javac -d . Digest.java

.PHONY: clean
clean:
	
