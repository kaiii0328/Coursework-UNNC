#pragma once
#include "DisplayableObject.h"


class Fence : public DisplayableObject
{
public:

	Fence() {
		init();
	};
	~Fence() {};

	void Display();
	void init();

private:
	std::string modelpath = "Model/fence.obj";

};