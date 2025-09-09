import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Menu, X, Users, MessageCircle, Target, Star } from "lucide-react";

const Navigation = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="sticky top-0 z-50 bg-background/95 backdrop-blur-sm border-b border-sage-green/20">
      <div className="container mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <div className="flex items-center space-x-2">
            <div className="w-8 h-8 bg-gradient-primary rounded-full flex items-center justify-center">
              <Users className="w-5 h-5 text-white" />
            </div>
            <span className="text-xl font-bold text-forest-green">RuralReach</span>
          </div>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center space-x-8">
            <a href="#features" className="text-earth-brown hover:text-forest-green transition-smooth">
              Features
            </a>
            <a href="#how-it-works" className="text-earth-brown hover:text-forest-green transition-smooth">
              How It Works
            </a>
            <a href="#mentors" className="text-earth-brown hover:text-forest-green transition-smooth">
              Find Mentors
            </a>
            <a href="#success-stories" className="text-earth-brown hover:text-forest-green transition-smooth">
              Success Stories
            </a>
          </div>

          {/* Desktop CTA */}
          <div className="hidden md:flex items-center space-x-4">
            <Button variant="ghost" size="sm">
              Sign In
            </Button>
            <Button variant="hero" size="sm">
              Get Started
            </Button>
          </div>

          {/* Mobile menu button */}
          <div className="md:hidden">
            <Button
              variant="ghost"
              size="icon"
              onClick={() => setIsOpen(!isOpen)}
              aria-label="Toggle menu"
            >
              {isOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
            </Button>
          </div>
        </div>

        {/* Mobile Navigation */}
        {isOpen && (
          <div className="md:hidden py-4 border-t border-sage-green/20">
            <div className="flex flex-col space-y-4">
              <a href="#features" className="text-earth-brown hover:text-forest-green transition-smooth px-4 py-2">
                Features
              </a>
              <a href="#how-it-works" className="text-earth-brown hover:text-forest-green transition-smooth px-4 py-2">
                How It Works
              </a>
              <a href="#mentors" className="text-earth-brown hover:text-forest-green transition-smooth px-4 py-2">
                Find Mentors
              </a>
              <a href="#success-stories" className="text-earth-brown hover:text-forest-green transition-smooth px-4 py-2">
                Success Stories
              </a>
              <div className="flex flex-col space-y-2 px-4 pt-4">
                <Button variant="ghost" size="sm" className="justify-start">
                  Sign In
                </Button>
                <Button variant="hero" size="sm" className="justify-start">
                  Get Started
                </Button>
              </div>
            </div>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navigation;