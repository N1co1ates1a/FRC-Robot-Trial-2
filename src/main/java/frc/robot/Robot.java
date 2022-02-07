
package frc.robot;

import java.nio.file.PathMatcher;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.simulation.SimHooks;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton; 
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;




/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private final PWMVictorSPX left_motor_1 = new PWMVictorSPX(1);
  private final PWMVictorSPX left_motor_2 = new PWMVictorSPX(2);
  private final PWMVictorSPX left_motor_3 = new PWMVictorSPX(3);

  private final PWMVictorSPX right_motor_1 = new PWMVictorSPX(4);
  private final PWMVictorSPX right_motor_2 = new PWMVictorSPX(5);
  private final PWMVictorSPX right_motor_3 = new PWMVictorSPX(6);

  private final PWMVictorSPX elevator_1 = new PWMVictorSPX(7);
  private final PWMVictorSPX elevator_2 = new PWMVictorSPX(8);

  private final PWMVictorSPX intake_1 = new PWMVictorSPX(9);
  private final PWMVictorSPX intake_2 = new PWMVictorSPX(10);

  private final Joystick xbox_s = new Joystick(11);
  
  private final JoystickButton intake_move_B = new JoystickButton(xbox_s, 1);
  private final JoystickButton el1_A = new JoystickButton(xbox_s, 3);
  private final JoystickButton el2_X = new JoystickButton(xbox_s, 4);
  private final JoystickButton el3_Y = new JoystickButton(xbox_s, 5);

  private final MotorController the_intake = new MotorControllerGroup(intake_1,intake_2);
  private final MotorController elevator = new MotorControllerGroup(elevator_1, elevator_2);

  private final MotorController left_motors = new MotorControllerGroup(left_motor_1, left_motor_2,left_motor_3);
  private final MotorController right_motors = new MotorControllerGroup(right_motor_1, right_motor_2, right_motor_3);
  private final DifferentialDrive chasis = new DifferentialDrive(left_motors,right_motors);

  private final Timer tiktak = new Timer();

  private final Encoder encoder = new Encoder(0, 1, false);
  
  private final double elevator_fixed = 10.0;
  private final double elevator_level_1 = 15.0;
  private final double elevator_level_2 = 40.0;
  private final double elevator_level_3 = 60.0;

  Robot call = new Robot();//Burayı her bölüme ayrıca yazmalı mıyım bilemedim başa yazdım çalışır mı ??

  private final void elevator_set_level_1(){

    if(el1_A.getRawButtonPressed){

    if(encoder.getDistance() < elevator_level_1 - elevator_fixed){
      elevator.set(0.7);
    }
    else if(encoder.getDistance() == elevator_level_1 - elevator_fixed){
      elevator.set(0);
    }
    else if(encoder.getDistance() > elevator_level_1 - elevator_fixed){
      elevator.set(-0.7);
    }

   }

  }

  private final void elevator_set_level_2(){

    if(el2_X.getRawButtonPressed){

    if(encoder.getDistance() < elevator_level_2 - elevator_fixed){
      elevator.set(0.7);
    }
    else if(encoder.getDistance() == elevator_level_2 - elevator_fixed){
      elevator.set(0);
    }
    else if(encoder.getDistance() > elevator_level_2 - elevator_fixed){
      elevator.set(-0.7);
    }

   }

  }

  private final void elevator_set_level_3(){

    if(el3_Y.getRawButtonPressed){

    if(encoder.getDistance() < elevator_level_3 - elevator_fixed){
      elevator.set(0.7);
    }
    else if(encoder.getDistance() == elevator_level_3 - elevator_fixed){
      elevator.set(0);
    }
    else if(encoder.getDistance() > elevator_level_3 - elevator_fixed){
      elevator.set(-0.7);
    }
    
   }

  }

  private final void intake_mechanism(){

    if(intake_move_B.getRawButtonPressed()){
      the_intake.set(0.7);

    }else{
      the_intake.set(0.0);
      
    }

  }



  @Override
  public void robotInit() { //robotInit 

    encoder.setDistancePerPulse(5.0/24);

    
  }

 
  @Override
  public void robotPeriodic() {}//robotPeriodic 

  
  @Override
  public void autonomousInit () {//autonomousInit 

   SimHooks.restartTiming();
   /*umarım sandığım işe yarıyordur ve zamanlayıcıyı sıfırlatıp başlatır
   muadili
    tiktak.reset();
    tiktak.start();

   */
   
  }


  @Override
  public void autonomousPeriodic() {//autonomousPeriodic 
    
    
    call.elevator_set_level_1();

    if(tiktak.get() <= 5.0){// ilk 5 saniye otonom

      chasis.tankDrive(0.5, 0.5);

    }
    else if(tiktak.get() > 5.0 && tiktak.get() <= 10.0){// ilk 10 saniye otonom

      chasis.tankDrive(0.3, 0.3);

    }else {

      elevator.set(0);
      chasis.tankDrive(0.0, 0.0);

    }


    call.close();// delirtti yine VS Code uyarıları ondan bu burada
    
  }//autonomousPeriodic 


  @Override
  public void teleopInit() {//teleopInit


  }

  @Override
  public void teleopPeriodic() {//teleopPeriodic 
    chasis.tankDrive(xbox_s.getRawAxis(1), xbox_s.getRawAxis(1));
    //ikisi de aynı sayı olursa sanıyorum tek joystik parçası ile bu iş hallolur (ikisine de 1 verdim)

    call.intake_mechanism();
    call.elevator_level_1();
    call.elevator_level_2();
    call.elevator_level_3();
  
  }
  

  @Override
  public void disabledInit() {}


  @Override
  public void disabledPeriodic() {

    chasis.set(0,0);
    elevator.set(0);
    the_intake.set(0);

  }


  @Override
  public void testInit() {}


  @Override
  public void testPeriodic() {}
}
