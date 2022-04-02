@echo OFF
CD../../src 
CD
javac *.java
java -Djava.security.policy=policy.txt BankServer