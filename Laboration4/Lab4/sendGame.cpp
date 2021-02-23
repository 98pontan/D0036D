#include <iostream>
#include <WS2tcpip.h>
#include "sendGame.h"
#include <string>

#define _WIN32_WINNT 0x501

#pragma comment (lib, "Ws2_32.lib")

using namespace std;

int main() {
	sendGame();
}

sendGame::sendGame() {

	WSADATA wsdata;
	int wsCheck;
	WORD ver;
	int portNum = 54000;

	SOCKET s;

	ver = MAKEWORD(2, 2);
	wsCheck = WSAStartup(ver, &wsdata);

	if (wsCheck != 0) {
		cerr << "cant initialize" << endl;
		return;
	}

	// Socket
	s = socket(AF_INET, SOCK_STREAM, 0);

	if (s == INVALID_SOCKET) {
		cerr << "cant create socket" << endl;
		return;

		// Bind ip address and port
		sockaddr_in target;
		target.sin_family = AF_INET;
		target.sin_port = htons(portNum);
		inet_pton(AF_INET, "127.0.0.1", &target.sin_addr);

		//bind(s, (sockaddr*)&target, sizeof(target));


	}
}


void sendGame::GUIcommunication(int id, Coordinate pos)
{
	int x = pos.x;
	int y = pos.y;
	int color = id;
	sockaddr_in serverGUI;
	SOCKET socketGUI;
	int sendOK;

	serverGUI.sin_family = AF_INET;
	serverGUI.sin_port = htons(3000);
	
	socketGUI = socket(AF_INET, SOCK_DGRAM, 0);

	std::string message = std::to_string(x)+ ":" + std::to_string(y) + ":" + std::to_string(color);

	sendOK = sendto(socketGUI, message.c_str(), message.size(), 0, (sockaddr*)&serverGUI, sizeof(serverGUI));

	if (sendOK == SOCKET_ERROR) {
		std::cout << "SOCKET ERROR! " << WSAGetLastError() << endl;
	}

	closesocket(socketGUI);
	WSACleanup();

}

