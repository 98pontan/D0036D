#define _WIN32_WINNT 0x501
#include "GameProtocol.h"

#pragma comment (lib, "Ws2_32.lib")
class sendGame {
private:

	

public:
	sendGame();

	void GUIcommunication(int id, Coordinate pos);
		

	
};

struct DatagramPacket {
	byte first = 0;
	byte second = 0;
};
