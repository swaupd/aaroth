import Navbar from "../Components/Navbar";
import Hero from "../Components/Hero";

function BCH() {
  return (
    <>
      <Navbar /> {/* Add this line */}{" "}
      {/* If you want to include a hero section */}
      <h1>This is our BCH</h1>
    </>
  );
}

export default BCH;
/*import Hero from "../Components/Hero";
import Navbar from "../Components/Navbar";

function Home() {
  return (
    <>
      <Navbar />
      <Hero
        cName="hero"
        videoSrc="videoplayback.mp4"
        title="Welcome To Crieya Innovation Club"
        buttonText="Explore"
        url="/"
        btnClass="show"
      />
    </>
  );
}

export default Home;*/
