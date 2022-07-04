#pragma once
#include "DisplayableObject.h"

class Floor :
	public DisplayableObject
{
public:
	Floor(){};
	Floor(int texid) {
		textureid = texid;
	}
	~Floor(){};

	void Display();

private:
	int textureid;
};

