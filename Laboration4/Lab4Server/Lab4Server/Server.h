#pragma once
#pragma comment (lib, "ws2_32.lib")

#include <iostream>
#include <WS2tcpip.h>
#include <WinSock2.h>
#include "GameProtocol.h"
#include <vector>
#include <string>
#include <thread>
#include <stdlib.h>
class GameGrid {

private:
	bool** grid;


public:
	bool movePlayer(Coordinate position);
	GameGrid();
	~GameGrid();
	
};

class Server {
private:
	SOCKET s;
	bool endGame;
public:
	void initiate(int portNum, int maxClients);
	void start();
	void shutDown();
	void sendNewPlayerMsg(unsigned int* id, const char* name);
	void sendNewPosMsg(int* id, Coordinate* pos);
	void sendPlayerLeftMsg(int* id);
	void playerLost(int* id);
	void disconnectPlayer(int* id);
	bool currentState(int* id);



};
