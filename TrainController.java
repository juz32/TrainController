
public class TrainController {
private double currentSpeed;
private double commandedSpeed;
private double commandedPower;

private double speedLimit;
private double authority; 

private double KI;
private double KP;
private double uk = 0;
private double uk_prev = 0;
private double ek = 0;
private double ek_prev = 0;
private double maxEnginePower = 120000; //in kiloWatts
private double trainSamplePeriod = .02;
private double trainSampleRate = 1/trainSamplePeriod; // samples per second for .02 sample interval

	
	public static void main(String[] args) throws InterruptedException {
		TrainControllerGUI gui = new TrainControllerGUI();
	}
	
	public TrainController(){
		currentSpeed = 0;
		commandedSpeed = 0;
		commandedPower = 0;
		
		speedLimit = 50;
		authority = 500;
		
		KI = .2;
		KP = .7;
	}
	public double getCurrentSpeed(){
		return currentSpeed;
	}
	public double getCommandedSpeed(){
		return commandedSpeed;
	}
	public double getCommandedPower(){
		return calculatePower();
	}
	public double getSpeedLimit(){
		return speedLimit;
	}
	public double getAuthority(){
		return authority;
	}
	public void setCommandedSpeed(double value){
		if(value < speedLimit) commandedSpeed = value;
		else commandedSpeed = speedLimit;
	}
	
	public double calculatePower(){
		ek = commandedSpeed - currentSpeed;
		uk = uk_prev + trainSamplePeriod/2 * (ek + ek_prev);
		commandedPower = KI*(ek) + KP*(uk);
		System.out.println("Commanded Power: " + commandedPower);
		if(commandedPower > maxEnginePower){
			uk = uk_prev;
			commandedPower = KP*(ek) + KI*(uk);
		}
		System.out.println("ek: " + ek + " uk: " + uk);
		ek_prev = ek;
		uk_prev = uk;
		return commandedPower;
	}
	
	
}
