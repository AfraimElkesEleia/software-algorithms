class HashTable(private val initialSize:Int = 3){
  inner class KeyValuePair(val key:String , var value:String)
  val capacity = initialSize
  private var entries = Array<KeyValuePair?>(capacity){null}
  private var entriesCount = 0
  fun hashKey(key:String):Int{
    val bytes = key.toByteArray()
    var hash = 2166136261u
    val fnPrime = 16777619u
    for(byte in bytes){
      hash = hash xor byte.toUInt()
      hash *= fnPrime
    }
    return (hash.toInt() and Int.MAX_VALUE) % entries.size
  }
  fun collisionHandling(key:String,hash:Int,setVal:Boolean):Int{
    var newHash:Int = hash
    for(i in 0 until entries.size){
      newHash = (hash+i) % entries.size
      print("[coll] " + key+ " " + hash + ", new hash: " + newHash)
    
    if(setVal && (entries[newHash] == null || entries[newHash]?.key == key))
    return newHash
    else if(!setVal &&(entries[newHash]?.key == key )) return newHash
    else continue
    }
    return -1
  }
  fun addEntry(key:String,value:String){
    var hash = hashKey(key)
    if(entries[hash] != null && entries[hash]?.key != key){
      hash = collisionHandling(key,hash,true)
    }
    if(hash == -1)
    {
      throw Exception("Invalid Hashtable!!!!");
    }
    if (entries[hash] == null){
      val pair = KeyValuePair(key,value)
      entries[hash] = pair
      entriesCount++
    }else if(entries[hash]?.key == key){
      entries[hash]?.value = value
    }
    else{
      throw Exception("Invalid Hashtable!!!!");
    }
  }
  
}

fun main() {
  println("Try clicking the Run button.")
}