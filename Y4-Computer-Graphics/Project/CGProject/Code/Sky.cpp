
//#include "Shader.h"
#include "GLM/glm.hpp"
#include "GLM/gtc/matrix_transform.hpp"
#include "GLM/gtc/type_ptr.hpp"
#define GLEW_STATIC
#include <GL/glew.h>
#include "Sky.h"

Sky::Sky(float skyboxSize, glm::mat4 projMatrix, glm::mat4 viewMatrix) {

    for (int i = 0; i < 6; i++) {
        texids[i] = Scene::GetTexture("Textures/skybox0/f" + std::to_string(i) + ".bmp");
    }
}


GLuint Sky::getTexID() {
    return textureID;
}

void Sky::HandleKey(unsigned char key, int state, int x, int y)
{
    if (state == 1) {
        switch (key)
        {
        case 'o':
            textureID = (textureID + 1) % 4;
            for (int i = 0; i < 6; i++)
                texids[i] = Scene::GetTexture("Textures/skybox" + std::to_string(textureID) + "/f" + std::to_string(i) + ".bmp");
            break;
        default:
            break;
        }
    }

    
}

Sky::~Sky() {
    //glDeleteTextures(1, &textureID);
    textureID = 0;
}

void Sky::Display() {
    glPushMatrix();

    //glDisable(GL_LIGHTING);

    // Set fill to be invisible (only texture is rendered)
    glColor4f(1.f, 1.f, 1.f, 1.f);
    glEnable(GL_TEXTURE_2D); // Enable 2D texturing

    /* Set the position, scale and rotation */
    glTranslatef(pos[0], pos[1], pos[2]);
    glScalef(scale[0], scale[1], scale[2]);
    glRotatef(rotation[0], 1.0f, 0.0f, 0.0f); // angle rx about (1,0,0)
    glRotatef(rotation[1], 0.0f, 1.0f, 0.0f); // angle ry about (0,1,0)
    glRotatef(rotation[2], 0.0f, 0.0f, 1.0f); // angle rz about (0,0,1)
    

    // LEFT SIDE
    glBindTexture(GL_TEXTURE_2D, texids[0]); // skybox_left, f0.bmp
    glBegin(GL_QUADS);
    glTexCoord2f(1.f, 1.f);  // (u,v) = (1,1)
    glVertex3f(-1.f, 1.f, -1.f);
    glTexCoord2f(0.f, 1.f);  // (u,v) = (0,1)
    glVertex3f(-1.f, 1.f, 1.f);
    glTexCoord2f(0.f, 0.f);  // (u,v) = (0,0)
    glVertex3f(-1.f, 0.f, 1.f);
    glTexCoord2f(1.f, 0.f);  // (u,v) = (1,0)
    glVertex3f(-1.f, 0.f, -1.f);
    glEnd();

    // RIGHT SIDE
    glBindTexture(GL_TEXTURE_2D, texids[1]); // skybox_right, f1.bmp
    glBegin(GL_QUADS);
    glTexCoord2f(1.f, 1.f);  // (u,v) = (1,1)
    glVertex3f(1.f, 1.f, 1.f);
    glTexCoord2f(0.f, 1.f);  // (u,v) = (0,1)
    glVertex3f(1.f, 1.f, -1.f);
    glTexCoord2f(0.f, 0.f);  // (u,v) = (0,0)
    glVertex3f(1.f, 0.f, -1.f);
    glTexCoord2f(1.f, 0.f);  // (u,v) = (1,0)
    glVertex3f(1.f, 0.f, 1.f);
    glEnd();

    //  FAR SIDE
    glBindTexture(GL_TEXTURE_2D, texids[2]); // skybox_front, f2.bmp
    glBegin(GL_QUADS);
    glTexCoord2f(1.f, 1.f);  // (u,v) = (1,1)
    glVertex3f(1.f, 1.f, -1.f);
    glTexCoord2f(0.f, 1.f);  // (u,v) = (0,1)
    glVertex3f(-1.f, 1.f, -1.f);
    glTexCoord2f(0.f, 0.f);  // (u,v) = (0,0)
    glVertex3f(-1.f, 0.f, -1.f);
    glTexCoord2f(1.f, 0.f);  // (u,v) = (1,0)
    glVertex3f(1.f, 0.f, -1.f);
    glEnd();

    // NEAR SIDE
    glBindTexture(GL_TEXTURE_2D, texids[3]); // skybox_back, f3.bmp
    glBegin(GL_QUADS);
    glTexCoord2f(1.f, 1.f);  // (u,v) = (1,1)
    glVertex3f(-1.f, 1.f, 1.f);
    glTexCoord2f(0.f, 1.f);  // (u,v) = (0,1)
    glVertex3f(1.f, 1.f, 1.f);
    glTexCoord2f(0.f, 0.f);  // (u,v) = (0,0)
    glVertex3f(1.f, 0.f, 1.f);
    glTexCoord2f(1.f, 0.f);  // (u,v) = (1,0)
    glVertex3f(-1.f, 0.f, 1.f);
    glEnd();

    // BOTTOM SIDE
    glBindTexture(GL_TEXTURE_2D, texids[4]); // skybox_down, f4.bmp
    glBegin(GL_QUADS);
    glTexCoord2f(0.f, 0.f);  // (u,v) = (0,0)
    glVertex3f(-1.f, 0.f, 1.f);
    glTexCoord2f(1.f, 0.f);  // (u,v) = (1,0)
    glVertex3f(1.f, 0.f, 1.f);
    glTexCoord2f(1.f, 1.f);  // (u,v) = (1,1)
    glVertex3f(1.f, 0.f, -1.f);
    glTexCoord2f(0.f, 1.f);  // (u,v) = (0,1)
    glVertex3f(-1.f, 0.f, -1.f);
    glEnd();

    // TOP SIDE
    glBindTexture(GL_TEXTURE_2D, texids[5]); // skybox_up, f5.bmp
    glBegin(GL_QUADS);
    glTexCoord2f(1.f, 1.f);  // (u,v) = (1,1)
    glVertex3f(1.f, 1.f, 1.f);
    glTexCoord2f(0.f, 1.f);  // (u,v) = (0,1)
    glVertex3f(-1.f, 1.f, 1.f);
    glTexCoord2f(0.f, 0.f);  // (u,v) = (0,0)
    glVertex3f(-1.f, 1.f, -1.f);
    glTexCoord2f(1.f, 0.f);  // (u,v) = (1,0)
    glVertex3f(1.f, 1.f, -1.f);
    glEnd();

    glDisable(GL_TEXTURE_2D);    // Disable texturing until reenabled
    glEnable(GL_LIGHTING);       // Reenable lighting after drawing skybox

    glPopMatrix();
}

/*
void Sky::Display() {
    if (!inited) {
        initShader();
        inited = true;       
    }    
    glDepthFunc(GL_LEQUAL);
    glDepthMask(GL_FALSE);
    skyShader->UseShader();
    skyShader->setMat4("projection", this->projMatrx);
    skyShader->setMat4("view", viewMatrx);
    renderSky();
}
*/