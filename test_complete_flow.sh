#!/bin/bash

echo "🎮 Testing Complete Game Flow - Simple Adventure"
echo "================================================"

echo "📝 Game Flow Test:"
echo "1. Start at Home → Go to Village → Get Sword"
echo "2. Go to Dungeon → Fight Skeleton → Get Golden Key"
echo "3. Go to Bridge → Use Key to unlock Mountain → Get Diamond"
echo "4. Go to Castle → Give Diamond to Guard → WIN!"

echo
echo "🎯 Testing the complete flow..."

(
echo "up"        # Home → Village
sleep 1
echo "collect"   # Get sword
sleep 1
echo "left"      # Village → Dungeon
sleep 1
echo "attack"    # Fight skeleton
sleep 1
echo "attack"    # Continue fighting
sleep 1
echo "right"     # Dungeon → Village
sleep 1
echo "up"       # Village → Bridge
sleep 1
echo "left"     # Try to access Mountain (should unlock with key)
sleep 1
echo "collect"  # Get diamond
sleep 1
echo "right"    # Mountain → Bridge
sleep 1
echo "up"       # Bridge → Castle (should ask for diamond)
sleep 1
echo "give"     # Give diamond to guard
sleep 1
echo "up"       # Enter castle (should win!)
sleep 1
echo "quit"
) | mvn compile exec:java -Dexec.mainClass=org.example.App

echo
echo "✅ Game flow test completed!"