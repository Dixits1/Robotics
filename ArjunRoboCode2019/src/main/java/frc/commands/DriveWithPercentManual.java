/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.OI;
import frc.subsystems.Drivetrain;
import harkerrobolib.commands.IndefiniteCommand;

public class DriveWithPercentManual extends IndefiniteCommand {

  private static final double SPEED_MULTIPLIER = 0.5;


  public DriveWithPercentManual() {
    requires(Drivetrain.getInstance());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double fSpeed = OI.getInstance().getDriver().getRightTrigger();
    double rSpeed = OI.getInstance().getDriver().getLeftTrigger();
    double turn = OI.getInstance().getDriver().getLeftX();
    
    Drivetrain.getInstance().getLeftMaster().set(ControlMode.PercentOutput, ((fSpeed - rSpeed) + turn) * SPEED_MULTIPLIER);
    Drivetrain.getInstance().getRightMaster().set(ControlMode.PercentOutput, ((fSpeed - rSpeed) - turn) * SPEED_MULTIPLIER);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drivetrain.getInstance().getLeftMaster().set(ControlMode.Disabled, 0);
    Drivetrain.getInstance().getRightMaster().set(ControlMode.Disabled, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
