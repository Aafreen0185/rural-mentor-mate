import { useState } from "react";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Separator } from "@/components/ui/separator";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { 
  User, 
  Mail, 
  Lock, 
  MapPin, 
  Building2, 
  Target, 
  Users, 
  Briefcase, 
  Heart,
  Rocket,
  Eye,
  EyeOff
} from "lucide-react";

interface RegistrationModalProps {
  isOpen: boolean;
  onClose: () => void;
  defaultTab?: "entrepreneur" | "mentor";
}

const RegistrationModal = ({ isOpen, onClose, defaultTab = "entrepreneur" }: RegistrationModalProps) => {
  const [activeTab, setActiveTab] = useState(defaultTab);
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    location: "",
    industry: "",
    experience: "",
    goals: "",
    expertise: "",
    bio: ""
  });

  const handleInputChange = (field: string, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // TODO: Implement actual registration logic
    console.log("Registration:", { type: activeTab, ...formData });
    onClose();
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-2xl max-h-[90vh] overflow-y-auto bg-gradient-card border-sage-green/30">
        <DialogHeader className="text-center">
          <div className="flex justify-center mb-4">
            <div className="w-12 h-12 bg-gradient-primary rounded-full flex items-center justify-center">
              <Users className="w-6 h-6 text-white" />
            </div>
          </div>
          <DialogTitle className="text-2xl font-bold text-foreground">Join RuralReach</DialogTitle>
          <p className="text-muted-foreground">Choose your path and start your journey today</p>
        </DialogHeader>

        <Tabs value={activeTab} onValueChange={(value: string) => setActiveTab(value as "entrepreneur" | "mentor")} className="w-full">
          <TabsList className="grid w-full grid-cols-2 mb-6 bg-sage-green/20">
            <TabsTrigger 
              value="entrepreneur" 
              className="data-[state=active]:bg-forest-green data-[state=active]:text-white flex items-center gap-2"
            >
              <Rocket className="w-4 h-4" />
              Entrepreneur
            </TabsTrigger>
            <TabsTrigger 
              value="mentor" 
              className="data-[state=active]:bg-warm-gold data-[state=active]:text-forest-green flex items-center gap-2"
            >
              <Heart className="w-4 h-4" />
              Mentor
            </TabsTrigger>
          </TabsList>

          {/* Entrepreneur Registration */}
          <TabsContent value="entrepreneur" className="space-y-6">
            <Card className="bg-gradient-card border-sage-green/30">
              <CardHeader className="pb-3">
                <CardTitle className="flex items-center gap-2 text-forest-green">
                  <Target className="w-5 h-5" />
                  Start Your Entrepreneurial Journey
                </CardTitle>
                <CardDescription>
                  Connect with experienced mentors who understand rural business challenges
                </CardDescription>
              </CardHeader>
            </Card>

            <form onSubmit={handleSubmit} className="space-y-4">
              {/* Basic Info */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="ent-name" className="text-foreground">Full Name</Label>
                  <div className="relative">
                    <User className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                    <Input
                      id="ent-name"
                      placeholder="Enter your full name"
                      value={formData.name}
                      onChange={(e) => handleInputChange("name", e.target.value)}
                      className="pl-10 border-sage-green/30 focus:border-forest-green"
                      required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="ent-email" className="text-foreground">Email Address</Label>
                  <div className="relative">
                    <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                    <Input
                      id="ent-email"
                      type="email"
                      placeholder="Enter your email"
                      value={formData.email}
                      onChange={(e) => handleInputChange("email", e.target.value)}
                      className="pl-10 border-sage-green/30 focus:border-forest-green"
                      required
                    />
                  </div>
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="ent-location" className="text-foreground">Location</Label>
                  <div className="relative">
                    <MapPin className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                    <Input
                      id="ent-location"
                      placeholder="City, State/Province"
                      value={formData.location}
                      onChange={(e) => handleInputChange("location", e.target.value)}
                      className="pl-10 border-sage-green/30 focus:border-forest-green"
                      required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="ent-industry" className="text-foreground">Industry</Label>
                  <Select onValueChange={(value) => handleInputChange("industry", value)}>
                    <SelectTrigger className="border-sage-green/30 focus:border-forest-green">
                      <Building2 className="w-4 h-4 text-muted-foreground mr-2" />
                      <SelectValue placeholder="Select your industry" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="agriculture">Agriculture & Farming</SelectItem>
                      <SelectItem value="handicrafts">Handicrafts & Artisan</SelectItem>
                      <SelectItem value="tourism">Tourism & Hospitality</SelectItem>
                      <SelectItem value="retail">Retail & E-commerce</SelectItem>
                      <SelectItem value="technology">Technology & Digital</SelectItem>
                      <SelectItem value="food">Food & Beverage</SelectItem>
                      <SelectItem value="other">Other</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="ent-password" className="text-foreground">Password</Label>
                <div className="relative">
                  <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                  <Input
                    id="ent-password"
                    type={showPassword ? "text" : "password"}
                    placeholder="Create a strong password"
                    value={formData.password}
                    onChange={(e) => handleInputChange("password", e.target.value)}
                    className="pl-10 pr-10 border-sage-green/30 focus:border-forest-green"
                    required
                  />
                  <Button
                    type="button"
                    variant="ghost"
                    size="icon"
                    className="absolute right-2 top-1/2 transform -translate-y-1/2 h-6 w-6"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
                  </Button>
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="ent-goals" className="text-foreground">Business Goals</Label>
                <Textarea
                  id="ent-goals"
                  placeholder="Tell us about your business goals and what kind of guidance you're looking for..."
                  value={formData.goals}
                  onChange={(e) => handleInputChange("goals", e.target.value)}
                  className="border-sage-green/30 focus:border-forest-green resize-none"
                  rows={3}
                  required
                />
              </div>

              <Button type="submit" variant="entrepreneur" className="w-full" size="lg">
                Start My Journey
              </Button>
            </form>
          </TabsContent>

          {/* Mentor Registration */}
          <TabsContent value="mentor" className="space-y-6">
            <Card className="bg-gradient-card border-sage-green/30">
              <CardHeader className="pb-3">
                <CardTitle className="flex items-center gap-2 text-warm-gold">
                  <Heart className="w-5 h-5" />
                  Share Your Expertise as a Mentor
                </CardTitle>
                <CardDescription>
                  Help rural entrepreneurs succeed by sharing your knowledge and experience
                </CardDescription>
              </CardHeader>
            </Card>

            <form onSubmit={handleSubmit} className="space-y-4">
              {/* Basic Info */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="mentor-name" className="text-foreground">Full Name</Label>
                  <div className="relative">
                    <User className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                    <Input
                      id="mentor-name"
                      placeholder="Enter your full name"
                      value={formData.name}
                      onChange={(e) => handleInputChange("name", e.target.value)}
                      className="pl-10 border-sage-green/30 focus:border-forest-green"
                      required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="mentor-email" className="text-foreground">Email Address</Label>
                  <div className="relative">
                    <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                    <Input
                      id="mentor-email"
                      type="email"
                      placeholder="Enter your email"
                      value={formData.email}
                      onChange={(e) => handleInputChange("email", e.target.value)}
                      className="pl-10 border-sage-green/30 focus:border-forest-green"
                      required
                    />
                  </div>
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="mentor-location" className="text-foreground">Location</Label>
                  <div className="relative">
                    <MapPin className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                    <Input
                      id="mentor-location"
                      placeholder="City, State/Province"
                      value={formData.location}
                      onChange={(e) => handleInputChange("location", e.target.value)}
                      className="pl-10 border-sage-green/30 focus:border-forest-green"
                      required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="mentor-experience" className="text-foreground">Years of Experience</Label>
                  <Select onValueChange={(value) => handleInputChange("experience", value)}>
                    <SelectTrigger className="border-sage-green/30 focus:border-forest-green">
                      <Briefcase className="w-4 h-4 text-muted-foreground mr-2" />
                      <SelectValue placeholder="Select experience level" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="5-10">5-10 years</SelectItem>
                      <SelectItem value="10-15">10-15 years</SelectItem>
                      <SelectItem value="15-20">15-20 years</SelectItem>
                      <SelectItem value="20+">20+ years</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="mentor-password" className="text-foreground">Password</Label>
                <div className="relative">
                  <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                  <Input
                    id="mentor-password"
                    type={showPassword ? "text" : "password"}
                    placeholder="Create a strong password"
                    value={formData.password}
                    onChange={(e) => handleInputChange("password", e.target.value)}
                    className="pl-10 pr-10 border-sage-green/30 focus:border-forest-green"
                    required
                  />
                  <Button
                    type="button"
                    variant="ghost"
                    size="icon"
                    className="absolute right-2 top-1/2 transform -translate-y-1/2 h-6 w-6"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
                  </Button>
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="mentor-expertise" className="text-foreground">Areas of Expertise</Label>
                <Input
                  id="mentor-expertise"
                  placeholder="e.g., Marketing, Finance, Operations, Strategy..."
                  value={formData.expertise}
                  onChange={(e) => handleInputChange("expertise", e.target.value)}
                  className="border-sage-green/30 focus:border-forest-green"
                  required
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="mentor-bio" className="text-foreground">Professional Bio</Label>
                <Textarea
                  id="mentor-bio"
                  placeholder="Share your background, achievements, and why you want to mentor rural entrepreneurs..."
                  value={formData.bio}
                  onChange={(e) => handleInputChange("bio", e.target.value)}
                  className="border-sage-green/30 focus:border-forest-green resize-none"
                  rows={3}
                  required
                />
              </div>

              <Button type="submit" variant="mentor" className="w-full" size="lg">
                Become a Mentor
              </Button>
            </form>
          </TabsContent>
        </Tabs>

        <div className="pt-4">
          <Separator className="bg-sage-green/30 mb-4" />
          <div className="text-center text-sm text-muted-foreground">
            Already have an account?{" "}
            <button
              type="button"
              className="text-forest-green hover:text-warm-gold transition-smooth font-medium"
              onClick={onClose}
            >
              Sign in here
            </button>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default RegistrationModal;