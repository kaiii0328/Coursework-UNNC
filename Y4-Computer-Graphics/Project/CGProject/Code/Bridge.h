#pragma once
#include "DisplayableObject.h"


class Bridge : public DisplayableObject
{
public:

	Bridge() {
		init();
	};
	~Bridge() {};

	void Display();
	void init();

};