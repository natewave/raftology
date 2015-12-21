package com.zengularity.raftology

sealed trait Entry
case class Heartbeat() extends Entry
case class AppendEntries(entry: Any) extends Entry // For sqlite, entry is going to be sql command

case object RequestVote
case object AcceptVote
case object DenyVote
