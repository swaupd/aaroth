import Navbar from "../Components/Navbar";
import Hero from "../Components/Hero";
import React from "react";
import prodimg from "../assets/product.png";
function Product() {
  return (
    <>
      <Navbar />
      <div className="product-page">
        <div className="product-container">
          <div className="product-image">
            <img src={prodimg} alt="Product" />
          </div>
          <div className="product-info">
            <h1>Product Name</h1>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit
              amet nulla auctor, vestibulum magna sed, convallis ex. Cum sociis
              natoque penatibus et magnis dis parturient montes, nascetur
              ridiculus mus. Integer posuere erat a ante venenatis dapibus
              posuere velit aliquet. Cras mattis consectetur purus sit amet
              fermentum. Cum sociis natoque penatibus et magnis dis parturient
              montes, nascetur ridiculus mus.
            </p>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit
              amet nulla auctor, vestibulum magna sed, convallis ex. Cum sociis
              natoque penatibus et magnis dis parturient montes, nascetur
              ridiculus mus. Integer posuere erat a ante venenatis dapibus
              posuere velit aliquet. Cras mattis consectetur purus sit amet
              fermentum. Cum sociis natoque penatibus et magnis dis parturient
              montes, nascetur ridiculus mus.
            </p>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit
              amet nulla auctor, vestibulum magna sed, convallis ex. Cum sociis
              natoque penatibus et magnis dis parturient montes, nascetur
              ridiculus mus. Integer posuere erat a ante venenatis dapibus
              posuere velit aliquet. Cras mattis consectetur purus sit amet
              fermentum. Cum sociis natoque penatibus et magnis dis parturient
              montes, nascetur ridiculus mus.
            </p>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit
              amet nulla auctor, vestibulum magna sed, convallis ex. Cum sociis
              natoque penatibus et magnis dis parturient montes, nascetur
              ridiculus mus. Integer posuere erat a ante venenatis dapibus
              posuere velit aliquet. Cras mattis consectetur purus sit amet
              fermentum. Cum sociis natoque penatibus et magnis dis parturient
              montes, nascetur ridiculus mus.
            </p>
          </div>
        </div>
      </div>
    </>
  );
}

export default Product;
