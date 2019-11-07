/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.commands.DriveWithPercentManual;
import harkerrobolib.subsystems.HSDrivetrain;
import harkerrobolib.wrappers.HSTalon;

/**
 * Add your docs here.
 */
public class Drivetrain extends HSDrivetrain{

  private static Drivetrain instance;

  private static final boolean L_TALON_INVERT = true;
  private static final boolean R_TALON_INVERT = false;
  private static final boolean L_VICTOR_INVERT = true;
  private static final boolean R_VICTOR_INVERT = false;
  
  private Drivetrain()
  {
    super(new HSTalon(RobotMap.LEFT_TALON_ID), 
          new HSTalon(RobotMap.RIGHT_TALON_ID),
          new VictorSPX(RobotMap.LEFT_VICTOR_ID), 
          new VictorSPX(RobotMap.RIGHT_VICTOR_ID));

    setupMotors();
  }

  private void setupMotors()
  {
    getLeftMaster().setInverted(L_TALON_INVERT);
    getRightMaster().setInverted(R_TALON_INVERT);
    getLeftFollower().setInverted(L_VICTOR_INVERT);
    getRightFollower().setInverted(R_VICTOR_INVERT);


    getLeftFollower().follow(getLeftMaster());
    getRightFollower().follow(getRightMaster());
  }


  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveWithPercentManual());
  }

  public static Drivetrain getInstance()
  {
    if(instance == null)
      instance = new Drivetrain();
    return instance;
  }
}
