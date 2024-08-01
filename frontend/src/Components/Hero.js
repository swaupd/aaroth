import "./HeroStyles.css";
import videoBg from "../assets/videoplayback.mp4";

function Hero(props) {
  return (
    <>
      <div className={props.cName}>
        <div className="background" />
        {props.HeroImg ? (
          <img className="hero-img" src={props.HeroImg} alt="Hero Image" />
        ) : (
          <video className="video" src={videoBg} autoPlay loop muted />
        )}
        <div className="hero-text">
          <h1>{props.title}</h1>
          <a href={props.url} className={props.btnClass}>
            {props.buttonText}
          </a>
        </div>
      </div>
    </>
  );
}

export default Hero;
