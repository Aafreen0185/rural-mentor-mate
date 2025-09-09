import { Button } from "@/components/ui/button";
import { ArrowRight, Users, MapPin, Sparkles } from "lucide-react";
import heroImage from "@/assets/hero-image.jpg";
import { useState } from "react";
import RegistrationModal from "@/components/RegistrationModal";

const Hero = () => {
  const [showRegistration, setShowRegistration] = useState(false);
  const [registrationTab, setRegistrationTab] = useState<"entrepreneur" | "mentor">("entrepreneur");

  const handleStartJourney = () => {
    setRegistrationTab("entrepreneur");
    setShowRegistration(true);
  };

  const handleBecomeMentor = () => {
    setRegistrationTab("mentor");
    setShowRegistration(true);
  };

  return (
    <section className="relative min-h-screen flex items-center bg-gradient-subtle overflow-hidden">
      {/* Background Image */}
      <div className="absolute inset-0 z-0">
        <img
          src={heroImage}
          alt="Rural entrepreneurs and mentors collaborating in a countryside setting"
          className="w-full h-full object-cover opacity-20"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-background/95 via-background/80 to-transparent" />
      </div>

      {/* Content */}
      <div className="container mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        <div className="max-w-4xl mx-auto text-center">
          {/* Badge */}
          <div className="inline-flex items-center gap-2 bg-sage-green/20 text-earth-brown px-4 py-2 rounded-full text-sm font-medium mb-8 border border-sage-green/30">
            <Sparkles className="w-4 h-4" />
            AI-Powered Mentorship Platform
          </div>

          {/* Main Heading */}
          <h1 className="text-4xl sm:text-5xl lg:text-6xl font-bold text-foreground mb-6 leading-tight">
            Connect Rural
            <span className="bg-gradient-primary bg-clip-text text-transparent"> Entrepreneurs </span>
            with Expert Mentors
          </h1>

          {/* Subheading */}
          <p className="text-lg sm:text-xl text-muted-foreground mb-8 max-w-2xl mx-auto leading-relaxed">
            Break barriers, build dreams. Our AI-powered platform connects rural entrepreneurs with experienced mentors, providing personalized guidance, resources, and actionable insights to grow your business.
          </p>

          {/* Stats */}
          <div className="flex flex-wrap justify-center gap-8 mb-10 text-sm">
            <div className="flex items-center gap-2 text-earth-brown">
              <Users className="w-5 h-5 text-forest-green" />
              <span className="font-semibold">500+</span> Active Mentors
            </div>
            <div className="flex items-center gap-2 text-earth-brown">
              <MapPin className="w-5 h-5 text-forest-green" />
              <span className="font-semibold">50+</span> Rural Communities
            </div>
            <div className="flex items-center gap-2 text-earth-brown">
              <Sparkles className="w-5 h-5 text-forest-green" />
              <span className="font-semibold">95%</span> Success Rate
            </div>
          </div>

          {/* CTA Buttons */}
          <div className="flex flex-col sm:flex-row gap-4 justify-center items-center">
            <Button variant="hero" size="lg" className="group" onClick={handleStartJourney}>
              Start Your Journey
              <ArrowRight className="w-5 h-5 group-hover:translate-x-1 transition-smooth" />
            </Button>
            <Button variant="subtle" size="lg" onClick={handleBecomeMentor}>
              Become a Mentor
            </Button>
          </div>

          {/* Social Proof */}
          <div className="mt-12 pt-8 border-t border-sage-green/20">
            <p className="text-sm text-muted-foreground mb-4">Trusted by entrepreneurs in</p>
            <div className="flex flex-wrap justify-center gap-6 text-sm text-earth-brown">
              <span>Agriculture</span>
              <span>•</span>
              <span>Handicrafts</span>
              <span>•</span>
              <span>Tourism</span>
              <span>•</span>
              <span>Tech</span>
              <span>•</span>
              <span>Retail</span>
            </div>
          </div>
        </div>
      </div>

      {/* Decorative Elements */}
      <div className="absolute top-1/4 right-10 w-20 h-20 bg-warm-gold/20 rounded-full blur-xl hidden lg:block" />
      <div className="absolute bottom-1/4 left-10 w-32 h-32 bg-forest-green/10 rounded-full blur-2xl hidden lg:block" />

      {/* Registration Modal */}
      <RegistrationModal 
        isOpen={showRegistration} 
        onClose={() => setShowRegistration(false)} 
        defaultTab={registrationTab}
      />
    </section>
  );
};

export default Hero;