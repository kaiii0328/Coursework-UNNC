#include <stdio.h>  
#define GLUT_DISABLE_ATEXIT_HACK  
#include <GL/glut.h>  

GLfloat AngleX;
GLfloat AngleY;


void drawCube() {

	glBegin(GL_POLYGON);
	glNormal3d(0.0f, 0.0f, 1.0f);
	glColor3f(1.0f, 0.0f, 0.0f); // red
	glVertex3f(1.0f, -1.0f, 1.0f);
	glColor3f(0.0f, 1.0f, 0.0f); // green    
	glVertex3f(1.0f, 1.0f, 1.0f);
	glColor3f(0.0f, 0.0f, 1.0f); // blue  
	glVertex3f(-1.0f, 1.0f, 1.0f);
	glColor3f(1.0f, 0.0f, 1.0f);
	glVertex3f(-1.0f, -1.0f, 1.0f);// purple 
	glEnd();
	// White  BACK
	glBegin(GL_POLYGON);
	glNormal3d(0.0f, 0.0f, -1.0f);
	glColor3f(1.0f, 1.0f, 1.0f);
	glVertex3f(1.0f, -1.0f, -1.0f);
	glVertex3f(1.0f, 1.0f, -1.0f);
	glVertex3f(-1.0f, 1.0f, -1.0f);
	glVertex3f(-1.0f, -1.0f, -1.0f);
	glEnd();
	// Blue TOP
	glBegin(GL_POLYGON);
	glNormal3d(0.0f, 1.0f, 0.0f);
	glColor3f(0.0f, 0.0f, 1.0f);
	glVertex3f(1.0f, 1.0f, 1.0f);
	glVertex3f(1.0f, 1.0f, -1.0f);
	glVertex3f(-1.0f, 1.0f, -1.0f);
	glVertex3f(-1.0f, 1.0f, 1.0f);
	glEnd();
	// Red BOTTOM
	glBegin(GL_POLYGON);
	glNormal3d(0.0f, -1.0f, 0.0f);
	glColor3f(1.0f, 0.0f, 0.0f);
	glVertex3f(-1.0f, -1.0f, 1.0f);
	glVertex3f(-1.0f, -1.0f, -1.0f);
	glVertex3f(1.0f, -1.0f, -1.0f);
	glVertex3f(1.0f, -1.0f, 1.0f);
	glEnd();
	// Green LEFT  
	glBegin(GL_POLYGON);
	glNormal3d(-1.0f, 0.0f, 0.0f);
	glColor3f(0.0f, 1.0f, 0.0f);
	glVertex3f(-1.0f, -1.0f, 1.0f);
	glVertex3f(-1.0f, 1.0f, 1.0f);
	glVertex3f(-1.0f, 1.0f, -1.0f);
	glVertex3f(-1.0f, -1.0f, -1.0f);
	glEnd();
	// Purple RIGH
	glBegin(GL_POLYGON);
	glNormal3d(1.0f, 0.0f, 0.0f);
	glColor3f(1.0f, 0.0f, 1.0f);
	glVertex3f(1.0f, -1.0f, -1.0f);
	glVertex3f(1.0f, 1.0f, -1.0f);
	glVertex3f(1.0f, 1.0f, 1.0f);
	glVertex3f(1.0f, -1.0f, 1.0f);
	glEnd();
}


void display(void)
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glRotatef(AngleX, 1.0f, 0.0f, 0.0f);
	glRotatef(AngleY, 0.0f, 1.0f, 0.0f);
	GLfloat scale = 0.1;
	glScalef(scale, scale, scale);
	drawCube();

	glBegin(GL_LINES);
	glColor3d(1, 1, 1);
	glVertex3d(0, 5, 0);
	glVertex3d(0, 0, 0);
	glEnd();

	glBegin(GL_LINES);
	glColor3d(1, 1, 1);
	glVertex3d(5, 0, 0);
	glVertex3d(0, 0, 0);
	glEnd();

	glBegin(GL_LINES);
	glColor3d(1, 1, 1);
	glVertex3d(0, 0, 5);
	glVertex3d(0, 0, 0);
	glEnd();


	//glTranslatef(0, 0, -4);

	
	glTranslatef(1, 5, 2);
	glRotatef(33.3, 1, 0, 0);
	glRotatef(-15.7, 0, 1, 0);
	glRotatef(-60, 0, 0, 1);
	glRotatef(-33.3, 1, 0, 0);
	glRotatef(15.7, 0, 1, 0);
	glTranslatef(-1, -5, -2);

	drawCube();

	glutSwapBuffers();
}

void reshape(int w, int h)
{
	GLfloat aspect = (GLfloat)w / (GLfloat)h;
	GLfloat nRange = 2.0f;
	glViewport(0, 0, w, h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	if (w <= h)
	{
		glOrtho(-nRange, nRange, -nRange * aspect, nRange * aspect, -nRange, nRange);
	}
	else
	{
		glOrtho(-nRange, nRange, -nRange / aspect, nRange / aspect, -nRange, nRange);
	}
}

void init()
{
	AngleX = 30.0f;
	AngleY = -45.0f;
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(60, 1, 0, 100);
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_DITHER);
	glShadeModel(GL_SMOOTH);
}

void main(int argc, char* argv[])
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	glutInitWindowSize(1200, 1200);
	glutCreateWindow("OpenGL Cube");
	init();
	glutDisplayFunc(display);
	glutReshapeFunc(reshape);
	glutMainLoop();
}
