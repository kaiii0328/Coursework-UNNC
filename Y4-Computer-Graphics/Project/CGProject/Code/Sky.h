#pragma once
#include "Texture.h"
#include "DisplayableObject.h"
#include "Input.h"

#include <iostream>
#include <string.h>
#include <cmath>
#include <vector>

#include "MyScene.h"    


class Sky : public DisplayableObject, public Input{
public:
    Sky() {};
    Sky(float skyboxSize, glm::mat4 projMatrx, glm::mat4 viewMatrix);
    ~Sky();

    void Display();
    //bool loadCubemap();
    void HandleKey(unsigned char key, int state, int x, int y);
    GLuint getTexID();
    //GLfloat* getVertices();
private:
    //GLuint textureID;
    int textureID =0;

    // filepaths to 6 faces
    std::vector<std::string> faces;

    // skybox vertices
    //std::vector<GLfloat> skyboxVertices;
    //float skyboxVertices[108];

    // VAO and VBO
    GLuint skyboxVAO, skyboxVBO;
    int texids[6] = { 0, 0, 0, 0, 0, 0 };

    bool inited = false;

    glm::mat4 projMatrx;
    glm::mat4 viewMatrx;
};
