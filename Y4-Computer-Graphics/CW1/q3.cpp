#include <GL/glut.h>
#include <math.h>


float rotate_angle = 0.0;
int counter = 0;
int time = 0;
float animateTime=0;
const double pi = 3.1415926;
double rotate_speed = 360 / 7;


void drawBase() {
	GLUquadric* base;
	base = gluNewQuadric();
	GLfloat baseRadius = 3;
	glTranslatef(0, 0, -30);
	glRotatef(120, 1, 0, 0);
	glRotatef(rotate_angle, 0, 0, 1);
	gluCylinder(base, baseRadius, baseRadius, 1, 50, 5);

	int num_trian = 12;
	double twice_pi = 2 * pi;
	glBegin(GL_TRIANGLE_FAN);
	glVertex2d(0, 0);
	for (int a = 0; a <= num_trian; a ++) {
				
		double x = baseRadius * cos(a * twice_pi / num_trian);
		double y = baseRadius * sin(a * twice_pi / num_trian);
		if (a % 2 == 0)
			glColor3f(0.45, 0.27, 0.28);
		else
			glColor3f(0.27, 0.53, 0.57);
		glVertex2d(x, y);
		
	}
	glEnd();
}

void drawCenter() {
	GLUquadric* bar;
	bar = gluNewQuadric();
	glPushMatrix();
	glTranslatef(0, 0, -4);
	//glRotatef(120, 1, 0, 0);
	glColor3f(1, 1, 1);
	gluCylinder(bar, 0.2,0.2, 4, 50,5);
	glPopMatrix();
}

void drawBars() {
	GLfloat baseRadius = 3;
	double iniAngle = pi / 6;
	GLfloat project_r = baseRadius * cos(iniAngle);
	for (int i = 0; i <= 3; i++) {
		glBegin(GL_LINE_STRIP);
		glVertex3f(baseRadius * sin(iniAngle + pi/2 * i), baseRadius * cos(iniAngle + pi/2 * i),0);
		glVertex3f(baseRadius * sin(iniAngle + pi / 2 * i), baseRadius * cos(iniAngle + pi / 2 * i), -2);
		glVertex3f(baseRadius * sin(-iniAngle + pi / 2 * i), baseRadius * cos(-iniAngle + pi / 2 * i), -2);
		glVertex3f(baseRadius * sin(-iniAngle + pi / 2 * i), baseRadius * cos(-iniAngle + pi / 2 * i), 0);
		glEnd();

		glBegin(GL_LINES);		
		glVertex3f( project_r * sin(pi/2*i), project_r * cos(pi / 2 * i), -2);
		glVertex3f(0,0,-4);
		glEnd();
	}
}

void drawCube(GLfloat x, GLfloat y, GLfloat z ) {
	glBegin(GL_QUADS);        // Draw The Cube Using quads
	glColor3f(1, 1, 1);
	glVertex3f(x,y,-z);    // Top Right Of The Quad (Top)
	glVertex3f(-x, y, -z);    // Top Left Of The Quad (Top)
	glVertex3f(-x, y, z);    // Bottom Left Of The Quad (Top)
	glVertex3f(x, y, z);    // Bottom Right Of The Quad (Top)
	glVertex3f(x, -y, -z);    // Top Right Of The Quad (Bottom)
	glVertex3f(-x, -y, z);    // Top Left Of The Quad (Bottom)
	glVertex3f(-x, -y, -z);    // Bottom Left Of The Quad (Bottom)
	glVertex3f(x, -y, -z);    // Bottom Right Of The Quad (Bottom)
	glVertex3f(x, y, z);    // Top Right Of The Quad (Front)
	glVertex3f(-x, y, z);    // Top Left Of The Quad (Front)
	glVertex3f(-x, -y, z);    // Bottom Left Of The Quad (Front)
	glVertex3f(x, -y, z);    // Bottom Right Of The Quad (Front)
	glVertex3f(x, -y, -z);    // Top Right Of The Quad (Back)
	glVertex3f(-x, -y, -z);    // Top Left Of The Quad (Back)
	glVertex3f(-x, y, -z);    // Bottom Left Of The Quad (Back)
	glVertex3f(x, y, -z);    // Bottom Right Of The Quad (Back)
	glColor3f(0.4, 0.4, 0.4);
	glVertex3f(-x, y, z);    // Top Right Of The Quad (Left)
	glVertex3f(-x, y, -z);    // Top Left Of The Quad (Left)
	glVertex3f(-x, -y, -z);    // Bottom Left Of The Quad (Left)
	glVertex3f(-x, -y, z);    // Bottom Right Of The Quad (Left)
	glColor3f(0.4, 0.4, 0.4);
	glVertex3f(x, y, -z);    // Top Right Of The Quad (Right)
	glVertex3f(x, y, z);    // Top Left Of The Quad (Right)
	glVertex3f(x, -y, z);    // Bottom Left Of The Quad (Right)
	glVertex3f(x, -y, -z);    // Bottom Right Of The Quad (Right)
	glEnd();            // End Drawing The Cube
}


void display() {	
	glClear(GL_COLOR_BUFFER_BIT);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	glColor3f(1, 1, 1);
	drawBase();
	
	drawCenter();
	
	drawBars();

	glutSwapBuffers();
}

void init(void) {
	glClearColor(0,0,0.f,1.f);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(45, 1, 0.1, 100);
}

void idleFunc() {
	// Get elapsed time (milliseconds)
	int t = glutGet(GLUT_ELAPSED_TIME);
	// Calculate difference in seconds between previous and current call 
	double diff = static_cast<double>(t - time) / 1000.0;
	rotate_angle = diff * rotate_speed;
	// Signify flag for display/bufferswap
	glutPostRedisplay();
}

int main(int argc, char* argv[]) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	glutInitWindowSize(600, 600);
	glutInitWindowPosition(0, 0);
	glutCreateWindow("TurnTable");
	init();
	glutDisplayFunc(display);
	glutIdleFunc(idleFunc);
	glutMainLoop();

}