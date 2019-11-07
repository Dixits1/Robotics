/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.commands.DriveWithPercentManual;
import frc.commands.ElevateWithPercentManual;
import frc.robot.RobotMap;
import harkerrobolib.wrappers.HSTalon;

//implement current limiting

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {

  private static Elevator instance;

  private static final boolean FOLLOWER_TALON_INVERT = true;
  private static final boolean MASTER_TALON_INVERT = true;
  private static final boolean L_VICTOR_INVERT = true;
  private static final boolean R_VICTOR_INVERT = true;
  public static final double GRAVITY_FF = 0.12;
  public static final double VOLTAGE_COMP = 10;

  private HSTalon masterTalon;
  private HSTalon followerTalon;
  private VictorSPX leftVictor;
  private VictorSPX rightVictor;

  private Elevator()
  {
    masterTalon = new HSTalon(RobotMap.ELEVATOR_MASTER_TALON_ID);
    followerTalon = new HSTalon(RobotMap.ELEVATOR_FOLLOWER_TALON_ID);
    leftVictor = new VictorSPX(RobotMap.ELEVATOR_LEFT_VICTOR_ID);
    rightVictor = new VictorSPX(RobotMap.ELEVATOR_RIGHT_VICTOR_ID);
  
    setupMotors();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ElevateWithPercentManual());
  }

  private void setupMotors()
  {
    getLeftVictor().setInverted(L_VICTOR_INVERT);
    getRightVictor().setInverted(R_VICTOR_INVERT);
    getFollowerTalon().setInverted(FOLLOWER_TALON_INVERT);
    getMasterTalon().setInverted(MASTER_TALON_INVERT);


    getLeftVictor().follow(getMasterTalon());
    getRightVictor().follow(getMasterTalon());
    getFollowerTalon().follow(getMasterTalon());

    initVoltageComp();
  }

  public VictorSPX getLeftVictor()
  {
    return leftVictor;
  }
  
  public VictorSPX getRightVictor()
  {
    return rightVictor;
  }

  public HSTalon getFollowerTalon()
  {
    return followerTalon;
  }

  public HSTalon getMasterTalon()
  {
    return masterTalon;
  }

  public static Elevator getInstance()
  {
    if(instance == null)
      instance = new Elevator();
    return instance;
  }

  public void setElevator(double output)
  {
    getMasterTalon().set(ControlMode.PercentOutput, output, DemandType.ArbitraryFeedForward.ArbitraryFeedForward, GRAVITY_FF);
  }

  public void initVoltageComp()
  {
    getMasterTalon().configVoltageCompSaturation(VOLTAGE_COMP);
    getMasterTalon().enableVoltageCompensation(true);
  }
}
