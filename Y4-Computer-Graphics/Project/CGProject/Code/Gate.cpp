#include <ModelLoader.h>
#include "Gate.h"

Model gatemodel;
void Gate::Display()
{
	//draw gate
	glPushMatrix();
	glTranslatef(-130, -110, 0);
	glScalef(35, 40, 30);
	glPushAttrib(GL_ALL_ATTRIB_BITS);
	float mat_ambient[] = { 0.2, 0.2, 0.2, 1.0f };
	float mat_diffuse[] = { 0.2, 0.2, 0.2, 1.0f };
	float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float mat_shininess[] = { 88.0 }; // 0-128

	glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);
	glColor3f(0.20, 0.20, 0.20);
	gatemodel.draw();
	glPopAttrib();
	glPopMatrix();
}

void Gate::init()
{
	//load gate
	gatemodel.load(modelpath.c_str());
}
