import React from "react";
import "bootstrap/dist/css/bootstrap.css";
import { Carousel, Container, Row, Col, Image, Card } from "react-bootstrap";
import Navbar from "../Components/Navbar";
import Hero from "../Components/Hero";
import FirstImg from "../Components/FirstImg.jpeg.png";
import SecondImg from "../Components/SecondImg.jpeg";
import ThirdImg from "../Components/ThirdImg.jpeg";
import photo1 from "../Components/1.jpg";
import photo2 from "../Components/2.jpeg";
import photo3 from "../Components/3.jpg";
import photo4 from "../Components/4.jpeg";

function Home() {
  return (
    <div style={{ display: "block" }}>
      <Navbar /> {/* Add the Navbar component here */}
      <Carousel>
        <Carousel.Item interval={1500}>
          <img className="d-block w-100" src={FirstImg} alt="Image One" />
          <Carousel.Caption></Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item interval={500}>
          <img className="d-block w-100" src={SecondImg} alt="Image Two" />
          <Carousel.Caption></Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item interval={500}>
          <img className="d-block w-100" src={ThirdImg} alt="Image Three" />
          <Carousel.Caption></Carousel.Caption>
        </Carousel.Item>
      </Carousel>
      <Container className="mt-5">
        <Row>
          <Col md={12}>
            <h2 className="text-center">Our Work</h2>
            <p className="text-center">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit
              amet nulla auctor, vestibulum magna sed, convallis ex.
            </p>
          </Col>
        </Row>

        <Row className="mt-4">
          <Col md={3}>
            <Card>
              <Image src={photo1} alt="1" fluid />
              <Card.Body>
                <Card.Title>Project 1</Card.Title>
                <Card.Text>
                  {" "}
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed
                  sit amet nulla auctor, vestibulum magna sed, convallis ex.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col md={3}>
            <Card>
              <Image src={photo2} alt="2" fluid />
              <Card.Body>
                <Card.Title>Project 2</Card.Title>
                <Card.Text>
                  {" "}
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed
                  sit amet nulla auctor, vestibulum magna sed, convallis ex.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col md={3}>
            <Card>
              <Image src={photo3} alt="3" fluid />
              <Card.Body>
                <Card.Title>Project 3</Card.Title>
                <Card.Text>
                  {" "}
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed
                  sit amet nulla auctor, vestibulum magna sed, convallis ex.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col md={3}>
            <Card>
              <Image src={photo4} alt="4" fluid />
              <Card.Body>
                <Card.Title>Project 4</Card.Title>
                <Card.Text>
                  {" "}
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed
                  sit amet nulla auctor, vestibulum magna sed, convallis ex.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
        </Row>

        <Row className="mt-5">
          <Col md={12}>
            <h2 className="text-center">Happy Clients</h2>
            <p className="text-center">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit
              amet nulla auctor, vestibulum magna sed, convallis ex.
            </p>
          </Col>
        </Row>

        <Row className="mt-4">
          <Col md={4}>
            <Card>
              <Card.Body>
                <Card.Text>
                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed
                  sit amet nulla auctor, vestibulum magna sed, convallis ex."
                </Card.Text>
                <Card.Title>John Doe</Card.Title>
                <Card.Subtitle>CEO, ABC Company</Card.Subtitle>
              </Card.Body>
            </Card>
          </Col>
          <Col md={4}>
            <Card>
              <Card.Body>
                <Card.Text>
                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed
                  sit amet nulla auctor, vestibulum magna sed, convallis ex."
                </Card.Text>
                <Card.Title>Jane Doe</Card.Title>
                <Card.Subtitle>CTO, XYZ Ltd.</Card.Subtitle>
              </Card.Body>
            </Card>
          </Col>
          <Col md={4}>
            <Card>
              <Card.Body>
                <Card.Text>
                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed
                  sit amet nulla auctor, vestibulum magna sed, convallis ex."
                </Card.Text>
                <Card.Title>James Smith</Card.Title>
                <Card.Subtitle>Marketing Director, DEF Inc.</Card.Subtitle>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default Home;
