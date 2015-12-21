package com.zengularity.raftology

import akka.actor._
import akka.cluster.ClusterEvent._
import akka.cluster.Member

case class Command(execute: String)

class Node extends Actor with ActorLogging {

  // --- Persistent state on all servers
  var nodes       : Seq[Member]     = Seq[Member]()
  var term        : Int             = 0
  var votedFor    : Option[Member]  = None

  // --- Volatile state on all servers, per server
  var logs        : Seq[Command]    = Seq[Command]()
  var commitIndex : Int             = 0 // Index of highest known committed log
  var lastApplied : Int             = 0 // Highest log entry applied to state machine

  // --- Volatile state on leaders
  var nextIndex: Map[Member, Int] = {
    // For each node, index of the next log entry to send to that node
    // Initialized at last log index + 1
    val lastLog = logs.length
    nodes.map((_, lastLog)).toMap
  }

  // For each server, index of highest log entry known to be replicated
  var matchIndex: Map[Member, Int] = nodes.map((_, 0)).toMap

  // Initial state as a follower
  def receive: Receive = follower

  def monitor: Receive = {
    case state: CurrentClusterState ⇒
      log.info("Current nodes: {}", state.members)

    case MemberUp(member: Member) ⇒
      log.info("Node is Up: {}", member)

      // Add member to nodes list
      nodes = nodes :+ member

    case UnreachableMember(member: Member) ⇒
      log.info("Node detected as unreachable: {}", member)

      // Remove member from nodes list
      nodes = nodes diff Seq(member: Member)

    case MemberRemoved(member, previousStatus) ⇒
      log.info(s"Node is Removed: $member, previous status: $previousStatus")

      // Remove member from nodes list
      nodes = nodes diff Seq(member)

    case _: ClusterDomainEvent ⇒ // ignore
  }

  def follower: Receive = monitor andThen {
    case Heartbeat() => log.info("got heartbeat as a follower")
  }

  def leader: Receive = monitor andThen {
    case Heartbeat() => log.info("got heartbeat as a leader")
  }

}

