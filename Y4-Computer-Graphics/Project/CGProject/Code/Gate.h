#pragma once
#include "DisplayableObject.h"


class Gate : public DisplayableObject
{
public:

	Gate() {
		init();
	};
	~Gate() {};

	void Display();
	void init();
private:
	std::string modelpath = "Model/gate.obj";
};