#include <ModelLoader.h>
#include "Bridge.h"


std::string modelpath = "Model/bridge.obj";
Model bridgeModel;

void Bridge::Display()
{
	glPushMatrix();

	// draw bridge
	glTranslatef(-120, -90, 220);
	glScalef(30, 30, 30);
	glPushAttrib(GL_ALL_ATTRIB_BITS);
	float mat_ambient[] = { 0.3, 0.3, 0.3, 1.0f };
	float mat_diffuse[] = { 0.3, 0.3, 0.3, 1.0f };
	float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float mat_shininess[] = { 120.0 }; // 0-128

	glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);
	glColor3f(0.3, 0.3, 0.3);
	bridgeModel.draw();
	glPopAttrib();
	glPopMatrix();
}

void Bridge::init()
{
	// load bridge
	bridgeModel.load(modelpath.c_str());
}
