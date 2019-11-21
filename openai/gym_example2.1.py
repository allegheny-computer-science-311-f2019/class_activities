import gym

env = gym.make("Taxi-v3")
env.render()

'''
env.reset()  # reset environment to a new, random state
env.render()

print("Action Space {}".format(env.action_space))
print("State Space {}".format(env.observation_space))



# (taxi row, taxi column, passenger index, dest. index)
state = env.encode(3, 1, 2, 0)
print("State:", state)

env.s = state
env.render()

print("Reward for state 328:", env.P[328])
'''
