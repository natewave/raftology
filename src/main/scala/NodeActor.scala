package com.zengularity.raft

import akka.actor.{Actor, ActorLogging, Props, ActorRef}

class NodeActor extends Actor with ActorLogging {

  var otherNodes = Set[ActorRef]

  var term: Int = 0

  val electionTimeout = ??? // randomize between 150ms and 300ms
  val heartbeatTimeout = ???

  var voteCount: Int = 0
  var votedFor: Option[ActorRef] = None

  def follower = {
    case StartTerm =>
      otherNodes.foreach { _ ! RequestVote }
      votedFor = Some(self)
      become(candidate)
    case RequestVote =>
      votedFor.collect {
        case None =>
          sender() ! Vote
          scheduleStartTerm()
      }
  }

  def candidate = {
    case Vote =>
      // count vote and check if majority
      if (majority) {
        become(leader)
      }
  }

  def leader = {

  }

  def receive = follower


  def scheduleStartTerm() = {
    Akka.system.scheduler.scheduleOnce(electionTimeout, self, StartTerm)
  }

}

object NodeActor {
  val props = Props[NodeActor]

  case object StartTerm
  case object RequestVote
  case object Vote
  case object AppendEntries
}
