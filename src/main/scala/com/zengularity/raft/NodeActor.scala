package com.zengularity.raft

import akka.actor.{Actor, ActorLogging, Props}

class NodeActor extends Actor with ActorLogging {

  def follower = {

  }

  def candidate = {

  }

  def leader = {

  }

  def receive = follower

}

object NodeActor {
  val props = Props[NodeActor]
}
