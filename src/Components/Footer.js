import React from "react";
import { Container, Row, Col, Stack } from "react-bootstrap";

function Footer() {
  return (
    <footer className="footer mt-auto">
      <Container fluid className="bg-dark text-white p-4">
        <Row className="justify-content-center">
          <Col md={4} className="text-center">
            <h5>Contact Us</h5>
            <p>Address: 123 Main St, Anytown, USA</p>
            <p>Phone: 555-555-5555</p>
            <p>
              Email:{" "}
              <a href="mailto:info@example.com" className="text-white">
                info@example.com
              </a>
            </p>
            <Stack className="align-items-center">
              <p>Copyright 2024 Our Company</p>
            </Stack>
          </Col>
        </Row>
      </Container>
    </footer>
  );
}

export default Footer;
