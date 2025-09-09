import { Button } from "@/components/ui/button";
import { Users, Mail, MapPin, Phone, Facebook, Twitter, Instagram, Linkedin } from "lucide-react";

const Footer = () => {
  return (
    <footer className="bg-forest-green text-white">
      <div className="container mx-auto px-4 sm:px-6 lg:px-8">
        {/* Main Footer Content */}
        <div className="py-16 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          {/* Brand Column */}
          <div className="lg:col-span-2">
            <div className="flex items-center space-x-2 mb-6">
              <div className="w-10 h-10 bg-warm-gold rounded-full flex items-center justify-center">
                <Users className="w-6 h-6 text-forest-green" />
              </div>
              <span className="text-2xl font-bold text-white">RuralReach</span>
            </div>
            <p className="text-sage-green/80 text-lg leading-relaxed mb-6 max-w-md">
              Empowering rural entrepreneurs through AI-powered mentorship connections. Break barriers, build dreams, and grow your business with expert guidance.
            </p>
            <div className="flex space-x-4">
              <Button variant="ghost" size="icon" className="text-sage-green/80 hover:text-white hover:bg-white/10">
                <Facebook className="w-5 h-5" />
              </Button>
              <Button variant="ghost" size="icon" className="text-sage-green/80 hover:text-white hover:bg-white/10">
                <Twitter className="w-5 h-5" />
              </Button>
              <Button variant="ghost" size="icon" className="text-sage-green/80 hover:text-white hover:bg-white/10">
                <Instagram className="w-5 h-5" />
              </Button>
              <Button variant="ghost" size="icon" className="text-sage-green/80 hover:text-white hover:bg-white/10">
                <Linkedin className="w-5 h-5" />
              </Button>
            </div>
          </div>

          {/* Quick Links */}
          <div>
            <h3 className="text-lg font-semibold text-white mb-6">Quick Links</h3>
            <ul className="space-y-4">
              <li>
                <a href="#features" className="text-sage-green/80 hover:text-white transition-smooth">
                  Platform Features
                </a>
              </li>
              <li>
                <a href="#how-it-works" className="text-sage-green/80 hover:text-white transition-smooth">
                  How It Works
                </a>
              </li>
              <li>
                <a href="#mentors" className="text-sage-green/80 hover:text-white transition-smooth">
                  Find Mentors
                </a>
              </li>
              <li>
                <a href="#success-stories" className="text-sage-green/80 hover:text-white transition-smooth">
                  Success Stories
                </a>
              </li>
              <li>
                <a href="#pricing" className="text-sage-green/80 hover:text-white transition-smooth">
                  Pricing
                </a>
              </li>
            </ul>
          </div>

          {/* Contact Info */}
          <div>
            <h3 className="text-lg font-semibold text-white mb-6">Get in Touch</h3>
            <ul className="space-y-4">
              <li className="flex items-center space-x-3">
                <Mail className="w-5 h-5 text-warm-gold" />
                <span className="text-sage-green/80">hello@ruralreach.com</span>
              </li>
              <li className="flex items-center space-x-3">
                <Phone className="w-5 h-5 text-warm-gold" />
                <span className="text-sage-green/80">1-800-RURAL-REACH</span>
              </li>
              <li className="flex items-center space-x-3">
                <MapPin className="w-5 h-5 text-warm-gold" />
                <span className="text-sage-green/80">Supporting rural communities nationwide</span>
              </li>
            </ul>
            <div className="mt-6">
              <Button variant="mentor" size="sm" className="bg-warm-gold/20 text-white hover:bg-warm-gold/30">
                Contact Support
              </Button>
            </div>
          </div>
        </div>

        {/* Bottom Footer */}
        <div className="border-t border-sage-green/30 py-8">
          <div className="flex flex-col md:flex-row justify-between items-center space-y-4 md:space-y-0">
            <div className="text-sage-green/60 text-sm">
              © 2024 RuralReach. All rights reserved. Empowering rural entrepreneurs since 2024.
            </div>
            <div className="flex space-x-6 text-sm">
              <a href="#privacy" className="text-sage-green/60 hover:text-white transition-smooth">
                Privacy Policy
              </a>
              <a href="#terms" className="text-sage-green/60 hover:text-white transition-smooth">
                Terms of Service
              </a>
              <a href="#accessibility" className="text-sage-green/60 hover:text-white transition-smooth">
                Accessibility
              </a>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;